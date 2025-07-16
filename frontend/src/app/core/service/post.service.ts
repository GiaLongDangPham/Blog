import { Injectable } from '@angular/core';
import { API_URL } from '../config/config';
import { HttpClient } from '@angular/common/http';
import { Post } from '../interface/post';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private apiURL = API_URL + 'api/v1/posts'

  constructor(private http: HttpClient) { }

  getPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(this.apiURL);
  }

  getPost(id: string): Observable<Post> {
    const url = `${this.apiURL}/${id}`;
    return this.http.get<Post>(url);
  }

  create(post: Post): Observable<string> {
    return this.http.post<string>(this.apiURL, post);
  }

  update(id: string, post: Post): Observable<string> {
    const url = `${this.apiURL}/${id}`;
    return this.http.patch<string>(url, post);
  }

  deletePost(id: string): Observable<void> {
    const url = `${this.apiURL}/${id}`;
    return this.http.delete<void>(url);
  }

}