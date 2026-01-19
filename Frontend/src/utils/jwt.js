// JWT utility functions
// Note: This is a simple base64 decoder. For production, use a proper JWT library like 'jwt-decode'

export const decodeJWT = (token) => {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    );
    return JSON.parse(jsonPayload);
  } catch (error) {
    console.error('Error decoding JWT:', error);
    return null;
  }
};

export const getRoleFromToken = (token) => {
  const decoded = decodeJWT(token);
  return decoded?.role || decoded?.authorities?.[0]?.authority || null;
};

export const getUserIdFromToken = (token) => {
  const decoded = decodeJWT(token);
  return decoded?.userId || decoded?.sub || decoded?.id || null;
};
