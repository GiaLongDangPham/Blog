import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Role } from '../interface/role';
import { HttpClient } from '@angular/common/http';
import { API_URL } from '../config/config';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  private apiURL = API_URL + 'api/v1/roles';

  constructor(
    private http: HttpClient
  ) { }


  getRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(this.apiURL);
  }
}
