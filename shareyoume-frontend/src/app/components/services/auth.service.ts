import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuthRequest, AuthResponse, SignupRequest } from '../models/user.model';
import { TokenStorageService } from './token-storage.service';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;
  private _isLoggedIn = new BehaviorSubject<boolean>(false);
  public isLoggedIn$ = this._isLoggedIn.asObservable();

  constructor(
    private http: HttpClient,
    private tokenStorage: TokenStorageService
  ) { }

  login(authRequest: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/signin`, authRequest).pipe(
      tap(response => {
        this.tokenStorage.saveToken(response.token);
        this.tokenStorage.saveUser({
          id: response.id,
          username: response.username,
          email: response.email,
          name: response.name,
          profileImageUrl: response.profileImageUrl,
          roles: response.roles 
        });
      })
    );
  }

  register(signupRequest: SignupRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, signupRequest);
  }

  logout(): void {
    this.tokenStorage.signOut();
    this._isLoggedIn.next(false);
  }

  isLoggedIn(): boolean {
    this._isLoggedIn.next(true);
    return !!this.tokenStorage.getToken();
  }

  getCurrentUser(): any {
    return this.tokenStorage.getUser();
  }
}