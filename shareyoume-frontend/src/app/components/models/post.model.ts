export interface Post {
    id: number;
    content: string;
    mediaName: string;
    mediaType: string;
    mediaUrl: String;
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
    mediaName?: string;
    mediaType?: string;
  }