import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL } from '../config/config';
import { Observable } from 'rxjs';
import { jwtDecode } from 'jwt-decode';
import { Token } from '../interface/token';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiURL = API_URL + 'api/v1/auth';

  constructor(
    private http: HttpClient,
    private router: Router,
    private messageService: MessageService
  ) { }

  login(credentials: {email: any, password: any}): Observable<any> {
    return this.http.post<any>(`${this.apiURL}/login`, credentials);
  }

  signup(credentials: {email: any, password: any, fullname: any}): Observable<any> {
    return this.http.post<any>(`${this.apiURL}/signup`, credentials);
  }

  // TOKEN

  setToken(token: string) {
    window.localStorage.setItem("auth_token", token);
  }

  getToken(): string {
    return window.localStorage.getItem("auth_token") || '';
  }

  removeToken(): void {
    window.localStorage.removeItem("auth_token");
  }

  setCurrentUsername(username: string) {
    window.localStorage.setItem("current_username", username);
  }

  getCurrentUsername(): string {
    return window.localStorage.getItem("current_username") || '';
  }

  removeCurrentUsername(): void {
    window.localStorage.removeItem("current_username");
  }

  
  canActive(): boolean {
    debugger
    const token = this.getToken();
    const decodedToken = jwtDecode<Token>(token);
    const role = decodedToken.role;

    if(role !== 'ROLE_USER') {
      return true;
    }
    else {
      alert("You do not have permission to access this resource.");
      return false;
    }
  }
}