import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loggedIn = false;

  constructor() { }

  login(username: String, password: String): boolean {
    // For now, fake login check
    if (username === 'user' && password === 'password') {
      this.loggedIn = true;
      localStorage.setItem('isLoggedIn', 'true');
      return true;
    }
    return false;
  }

  logout(): void {
    this.loggedIn = false;
    localStorage.removeItem('isLoggedIn');
  }

  isAuthenticated(): boolean {
    return localStorage.getItem('isLoggedIn') === 'true';
  }
}
