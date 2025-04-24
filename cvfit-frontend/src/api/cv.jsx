
import axios from 'axios';

export const getRolesByCvId = (cvId) =>
  axios.get(`/api/cvs/${cvId}/roles`).then(res => res.data);

export const getCvDetails = (cvId) =>
  axios.get(`/cv/${cvId}/details`).then(res => res.data);

export const getCvJobs = (cvId) =>
  axios.get(`/api/cvs/${cvId}/jobs`).then(res => res.data);

export const getUserCvs = (userId) =>
  axios.get(`/api/users/${userId}/cvs`).then(res => res.data);
