import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post, PostRequest } from '../models/post.model';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiUrl = `${environment.apiUrl}/posts`;

  constructor(private http: HttpClient) { }

  getAllPosts(page: number = 0, size: number = 10): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<any>(`${this.apiUrl}`, { params });
  }

  getPostsByUser(username: string, page: number = 0, size: number = 10): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<any>(`${this.apiUrl}/user/${username}`, { params });
  }

  createPost(formData: FormData): Observable<Post> {
    return this.http.post<Post>(`${this.apiUrl}/post`, formData);
  }

  deletePost(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  getMediaFile(mediaName: string): Observable<Blob>{
    return this.http.get(`${this.apiUrl}/media/${mediaName}`, {
      responseType: 'blob',
    });
  }
}