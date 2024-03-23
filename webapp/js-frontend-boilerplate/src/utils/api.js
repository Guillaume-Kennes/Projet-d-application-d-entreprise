/* eslint-disable import/no-cycle */

export default class API {

  static async call(endpoint, options = {}) {
    const { isAuthenticated, refreshAuthenticatedUser, getAuthenticatedUser } = await import("./auths");

    const API_URL = '/api';

    if (isAuthenticated()) {
      await refreshAuthenticatedUser();
    }

    const defaultHeaders = {
      'Content-Type': 'application/json',
    };

    const headers = isAuthenticated() ? { ...defaultHeaders, Authorization: `Bearer ${getAuthenticatedUser().token}` } : defaultHeaders;

    const opt = { ...options, headers };

    const response = await fetch(`${API_URL}/${endpoint}`, opt);
    const json = await response.json();

    if (!response.ok || json.error) {
      throw new Error(json.error || 'An error occurred');
    }

    return json;
  }

  static async get(endpoint, options = {}) {
    return API.call(endpoint, { ...options, method: 'GET' });
  }

  static async post(endpoint, options = {}) {
    return API.call(endpoint, { ...options, method: 'POST' });
  }
}