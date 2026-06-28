import axios from 'axios';

// BASE_URL matches Spring Boot: server.servlet.context-path=/api/v1.0
const BASE_URL = 'http://localhost:8080/api/v1.0';

const api = axios.create({
  baseURL: BASE_URL,
  withCredentials: true,   // sends HttpOnly JWT cookie on every request
  headers: { 'Content-Type': 'application/json' },
});

// ── UserController ──────────────────────────────────
export const register = (name, email, password) =>
  api.post('/register', { name, email, password });

export const getProfile = () =>
  api.get('/profile');

// ── AuthController ──────────────────────────────────
export const login = (email, password) =>
  api.post('/login', { email, password });

export const isAuthenticated = () =>
  api.get('/is-authenticated');

export const sendResetOtp = (email) =>
  api.post(`/send-reset-otp?email=${encodeURIComponent(email)}`);

export const resetPassword = (email, otp, newPassword) =>
  api.post('/reset-password', { email, otp, newPassword });

export const sendVerifyOtp = () =>
  api.post('/send-otp');

export const verifyOtp = (otp) =>
  api.post('/verify-otp', { otp });

export default api;
