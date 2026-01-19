import api from './api';
import { API_ENDPOINTS, STORAGE_KEYS, ROLES } from '../utils/constants';

export const authService = {
  signIn: async (signInData) => {
    const response = await api.post(API_ENDPOINTS.AUTH.SIGNIN, signInData);
    return response.data;
  },

  login: async (loginData) => {
    // Clear old authentication data first
    authService.logout();
    
    const response = await api.post(API_ENDPOINTS.AUTH.LOGIN, loginData);
    if (response.data.token) {
      localStorage.setItem(STORAGE_KEYS.TOKEN, response.data.token);
      // Extract role and userId from token
      const { getRoleFromToken, getUserIdFromToken } = await import('../utils/jwt');
      const role = getRoleFromToken(response.data.token);
      const userId = getUserIdFromToken(response.data.token);
      if (role) localStorage.setItem(STORAGE_KEYS.ROLE, role);
      if (userId) localStorage.setItem(STORAGE_KEYS.USER_ID, userId.toString());
    }
    return response.data;
  },

  logout: () => {
    localStorage.removeItem(STORAGE_KEYS.TOKEN);
    localStorage.removeItem(STORAGE_KEYS.USER);
    localStorage.removeItem(STORAGE_KEYS.ROLE);
    localStorage.removeItem(STORAGE_KEYS.USER_ID);
  },

  getToken: () => {
    return localStorage.getItem(STORAGE_KEYS.TOKEN);
  },

  getRole: () => {
    return localStorage.getItem(STORAGE_KEYS.ROLE);
  },

  getUserId: () => {
    return localStorage.getItem(STORAGE_KEYS.USER_ID);
  },

  setUserData: (userData) => {
    if (userData.role) localStorage.setItem(STORAGE_KEYS.ROLE, userData.role);
    if (userData.userId) localStorage.setItem(STORAGE_KEYS.USER_ID, userData.userId);
    if (userData.user) localStorage.setItem(STORAGE_KEYS.USER, JSON.stringify(userData.user));
  },

  isAuthenticated: () => {
    return !!localStorage.getItem(STORAGE_KEYS.TOKEN);
  },
};
