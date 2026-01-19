import { useState, useEffect } from 'react';
import { authService } from '../services/authService';
import { ROLES, STORAGE_KEYS } from '../utils/constants';

// Initialize state from localStorage
const getInitialAuthState = () => {
  const authenticated = authService.isAuthenticated();
  const role = authService.getRole();
  const id = authService.getUserId();
  return {
    isAuthenticated: authenticated,
    userRole: role,
    userId: id ? parseInt(id) : null,
  };
};

export const useAuth = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(() => getInitialAuthState().isAuthenticated);
  const [userRole, setUserRole] = useState(() => getInitialAuthState().userRole);
  const [userId, setUserId] = useState(() => getInitialAuthState().userId);
  const [loading] = useState(false);

  useEffect(() => {
    // Listen for storage changes (when login happens in another tab/window or after login)
    const handleStorageChange = (e) => {
      if (e.key === STORAGE_KEYS.TOKEN || e.key === STORAGE_KEYS.ROLE || e.key === STORAGE_KEYS.USER_ID || !e.key) {
        const newAuthenticated = authService.isAuthenticated();
        const newRole = authService.getRole();
        const newId = authService.getUserId();
        setIsAuthenticated(newAuthenticated);
        setUserRole(newRole);
        setUserId(newId ? parseInt(newId) : null);
      }
    };
    
    // Listen for custom storage event (for same-tab updates)
    window.addEventListener('storage', handleStorageChange);
    
    // Also listen for custom event we can trigger manually
    const handleCustomStorageChange = () => {
      const newAuthenticated = authService.isAuthenticated();
      const newRole = authService.getRole();
      const newId = authService.getUserId();
      setIsAuthenticated(newAuthenticated);
      setUserRole(newRole);
      setUserId(newId ? parseInt(newId) : null);
    };
    
    window.addEventListener('authChange', handleCustomStorageChange);
    
    return () => {
      window.removeEventListener('storage', handleStorageChange);
      window.removeEventListener('authChange', handleCustomStorageChange);
    };
  }, []);

  const login = async (loginData) => {
    const response = await authService.login(loginData);
    // Update state immediately after login by triggering custom event
    const authenticated = authService.isAuthenticated();
    const role = authService.getRole();
    const id = authService.getUserId();
    setIsAuthenticated(authenticated);
    setUserRole(role);
    setUserId(id ? parseInt(id) : null);
    // Also dispatch custom event for other components
    window.dispatchEvent(new Event('authChange'));
    return response;
  };

  const logout = () => {
    authService.logout();
    setIsAuthenticated(false);
    setUserRole(null);
    setUserId(null);
  };

  const isAdmin = () => userRole === ROLES.ADMIN;
  const isTeacher = () => userRole === ROLES.TEACHER;
  const isStudent = () => userRole === ROLES.STUDENT;

  return {
    isAuthenticated,
    userRole,
    userId,
    loading,
    login,
    logout,
    isAdmin,
    isTeacher,
    isStudent,
  };
};
