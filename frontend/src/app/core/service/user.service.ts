import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL } from '../config/config';
import { User } from '../interface/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class UserService {

  private apiURL = API_URL + 'api/v1/users'

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiURL);
  }

  getUser(id: string): Observable<User> {
    const url = `${this.apiURL}/${id}`;
    return this.http.get<User>(url);
  }

  create(user: User): Observable<string> {
    return this.http.post<string>(this.apiURL, user);
  }

  update(id: string, user: User): Observable<string> {
    const url = `${this.apiURL}/${id}`;
    return this.http.patch<string>(url, user);
  }

}