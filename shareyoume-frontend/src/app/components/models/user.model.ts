export interface User {
    id: number;
    username: string;
    email: string;
    name: string;
    profileImageUrl: string;
    bio: string;
    roles: string[];
  }
  
  export interface AuthResponse {
    token: string;
    type: string;
    id: number;
    username: string;
    email: string;
    name: string;
    profileImageUrl: string;
    roles: string[];
  }
  
  export interface AuthRequest {
    username: string;
    password: string;
  }
  
  export interface SignupRequest {
    username: string;
    email: string;
    password: string;
    name: string;
  }