import axios from "axios";
const jwtToken = localStorage.getItem("jwt")
export const API_BASE_URL = 'http://localhost:6565';
export const api = axios.create({
  baseURL: API_BASE_URL, 
  headers: {
    'Authorization':`Bearer ${jwtToken}`,
    'Content-Type': 'application/json',
  },
});

// Assuming you have the JWT token stored in a variable called jwtToken

