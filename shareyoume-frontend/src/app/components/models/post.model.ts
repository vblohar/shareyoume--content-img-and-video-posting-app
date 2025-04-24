export interface Post {
    id: number;
    content: string;
    mediaUrl: string;
    mediaType: string;
    createdAt: Date;
    user: {
      id: number;
      username: string;
      name: string;
      profileImageUrl: string;
    };
  }
  
  export interface PostRequest {
    content: string;
    mediaUrl?: string;
    mediaType?: string;
  }