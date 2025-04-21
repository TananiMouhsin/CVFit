package com.cvfit.cvfit.CVRoles;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.multipart.MultipartFile;

import io.github.cdimascio.dotenv.Dotenv;

public class CVRolesService {


    public static List<Map<String, String>> Scrape(String titleKeyword){
        return Stages_emplois_scrapping(titleKeyword); 
    }

    public static List<Map<String, String>> scrape_stages_emplois(String searchUrl, int maxPages) {
        List<Map<String, String>> jobList = new ArrayList<>();

        try {
            for (int i = 1; i <= maxPages; i++) {
                String url = searchUrl + "&page=" + i; // Ajouter la pagination
                Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
                Elements jobElements = doc.select(".job"); // Sélectionner les offres

                for (Element jobElement : jobElements) {
                    Map<String, String> jobData = new HashMap<>();
                    jobData.put("Title", jobElement.select(".jobtitle").text().trim().toLowerCase());
                    jobData.put("City", jobElement.select(".company-location").text().trim().toLowerCase());
                    jobData.put("Link", jobElement.select(".job a").attr("href"));

                    jobList.add(jobData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    
    public static List<Map<String, String>> Stages_emplois_scrapping(String titleKeyword) {
        
        String searchUrl;

        

        searchUrl = "https://www.stages-emplois.com/recherche-emploi.php?qemploi=" + titleKeyword.toLowerCase();
        List<Map<String, String>> jobList = scrape_stages_emplois(searchUrl, 10); 
        List<Map<String, String>> filteredJobs = new ArrayList<>();
        return jobList ; 
    }


    public static String[] GetRoles(MultipartFile file){
        String file_text = extractTextFromPdf(file);
        try{
            String finalresumetext =encodeString(file_text);
            String[] jobRoles = analyzeResumeForJobRoles(finalresumetext);


            return jobRoles;


        }catch(UnsupportedEncodingException e){
                e.printStackTrace();
        }
        return new String[] {"Failed to extract roles"};
    }
    


    public static String encodeString(String input) throws UnsupportedEncodingException {
        return URLEncoder.encode(input, "UTF-8");
    }

    public static String extractTextFromPdf(MultipartFile file) {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Uploaded file is empty");
            }

            try (InputStream inputStream = file.getInputStream();
                PDDocument document = PDDocument.load(inputStream)) {
                
                PDFTextStripper pdfStripper = new PDFTextStripper();
                return pdfStripper.getText(document);
                
            } catch (IOException e) {
                throw new RuntimeException("Error processing PDF file", e);
            }
    }


    public static String[] analyzeResumeForJobRoles(String resumeText) {
        Path path = Paths.get("cvfit/.").toAbsolutePath().getParent();
        String url = "https://api.openai.com/v1/chat/completions";
        Dotenv dotenv = Dotenv.configure().directory("cvfit/.env").load();
        String apiKey = dotenv.get("GPT_API_KEY");
        String model = "gpt-3.5-turbo";
        System.out.println(apiKey);

        if (apiKey == null || apiKey.isEmpty()) {
            return new String[] {"API Key not found in .env file!"};
        }

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");


            String body = "{\n" +
                          "  \"model\": \"" + model + "\",\n" +
                          "  \"messages\": [\n" +
                          "    {\"role\": \"system\", \"content\": \"You are a helpful assistant specializing in career guidance.\"},\n" +
                          "    {\"role\": \"user\", \"content\": \"Analyze the following resume and identify specific job roles or titles that the candidate is suitable for, based on their skills and experience. Return the roles in a clear, comma-separated list format without any text before or after .\\n\\nResume Text:\\n" + resumeText + "\"}\n" +
                          "  ],\n" +
                          "  \"max_tokens\": 1000,\n" +
                          "  \"temperature\": 0.7\n" +
                          "}";

            System.out.println("Body: " + body + "\n");

            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return extractJobRoles(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] extractJobRoles(String jsonResponse) {
        String[] roles = new String[0]; // Initialize with an empty array to avoid null errors
    
        try {
            JSONObject obj = new JSONObject(jsonResponse);
            JSONArray choices = obj.getJSONArray("choices");
    
            if (choices.length() > 0) {
                JSONObject firstChoice = choices.getJSONObject(0);
                JSONObject message = firstChoice.getJSONObject("message");
                String responseText = message.getString("content").trim();
    
                roles = responseText.split(","); // Split the response into roles
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    
        return roles; // Now it's always initialized
    }

}
