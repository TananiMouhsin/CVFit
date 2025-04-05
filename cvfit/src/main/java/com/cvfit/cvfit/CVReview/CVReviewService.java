package com.cvfit.cvfit.CVReview;

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
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import io.github.cdimascio.dotenv.Dotenv;

class Points {
    private String[] strengths;
    private String[] suggestions;

    public Points(String[] strengths, String[] suggestions) {
        this.strengths = strengths;
        this.suggestions = suggestions;
    }

    public String[] getStrengths() {
        return strengths;
    }

    public void setStrengths(String[] strengths) {
        this.strengths = strengths;
    }

    public String[] getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String[] suggestions) {
        this.suggestions = suggestions;
    }


}

public class CVReviewService {

    public static String ReviewCV(MultipartFile file) {

        String file_text = extractTextFromPdf(file);

        try {
  
            String finalresumetext = encodeString(file_text);

            String output = analyzeResumeWithGPT(finalresumetext);

            return separatePoints(output);

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
            return "Encoding Error: Failed to process the resume.";
        } catch (Exception e) {

            e.printStackTrace();
            return "Unknown Error: Failed to analyze the resume.";
        }
    }
    public static String separatePoints(String text) {
        String[] sections = text.split("Suggestions for improvement:");
    
        if (sections.length < 2) {
            throw new IllegalArgumentException("Could not parse strengths and suggestions properly.");
        }
    
        String strengthsSection = sections[0].replace("Strengths:", "").trim();
        String suggestionsSection = sections[1].trim();
        
    

        strengthsSection = cleanSection(strengthsSection);
        suggestionsSection = cleanSection(suggestionsSection);

        if (strengthsSection.endsWith("\\n")) {
            strengthsSection = strengthsSection.substring(0, strengthsSection.length() - 2).trim(); // Remove the trailing "\n"
        }
    

        String[] strengths = extractBulletPoints(strengthsSection);
        String[] suggestions = extractBulletPoints(suggestionsSection);
    

        Gson gson = new Gson();
        String jsonResult = gson.toJson(new Points(strengths, suggestions));
    
        return jsonResult;
    }
    
    private static String[] extractBulletPoints(String section) {
        String[] points = section.split("- ");
        List<String> cleanPoints = new ArrayList<>();
    
        for (String point : points) {
            point = point.trim();

            if (point.endsWith("\\n")) {
                point = point.substring(0, point.length() - 2).trim();
            }
            if (!point.isEmpty()) {
                cleanPoints.add(point);
            }
        }
    
        return cleanPoints.toArray(new String[0]);
    }
    
    private static String cleanSection(String section) {

        return section.trim(); 
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

    public static String analyzeResumeWithGPT(String resumeText) {
        Path path = Paths.get("cvfit/.").toAbsolutePath().getParent();
        String url = "https://api.openai.com/v1/chat/completions";
        Dotenv dotenv = Dotenv.configure().directory("C:\\Users\\DELL\\Desktop\\CVFIT\\CVFit\\cvfit\\.env").load();
        String apiKey = dotenv.get("GPT_API_KEY");
        String model = "gpt-3.5-turbo"; 
        System.out.println(apiKey);

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");


            String body = "{\n" +
                          "  \"model\": \"" + model + "\",\n" +
                          "  \"messages\": [\n" +
                          "    {\"role\": \"system\", \"content\": \"You are a helpful assistant specializing in resume analysis.\"},\n" +
                          "    {\"role\": \"user\", \"content\": \"You are an expert resume analyzer. Provide detailed feedback on the following resume: Resume Text: " + resumeText + "   Format the response as follows:  5 Strengths: [List of strengths separated by newlines] (return to line );; 5 Suggestions for improvement: [List of suggestions separated by newlines ] (return to line );; Avoid using characters with hex codes 0x5C and 0x2F. \"}\n" +
                          "  ],\n" +
                          "  \"max_tokens\": 1000,\n" +
                          "  \"temperature\": 0.7\n" +
                          "}";
            System.out.println("Body  : " + body + "\n");
            
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


            return extractContentFromResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content") + 11;
        int endMarker = response.indexOf("\"", startMarker); 
        return response.substring(startMarker, endMarker);
    }
    
}