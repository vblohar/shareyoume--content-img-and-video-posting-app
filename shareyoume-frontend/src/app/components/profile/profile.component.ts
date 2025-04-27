import { Component, OnInit } from '@angular/core';
import { Post } from '../models/post.model';
import { User } from '../models/user.model';
import { PostService } from '../services/post.service';
import { UserService } from '../services/user.service';
import { CommonModule } from '@angular/common';
import { PostListComponent } from "../post/post-list/post-list.component";
import { MatPaginatorModule } from '@angular/material/paginator';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-profile',
  imports: [CommonModule, PostListComponent, MatPaginatorModule, RouterLink],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})

export class ProfileComponent implements OnInit {
  user: User | null = null;
  posts: Post[] = [];
  currentPage = 0;
  pageSize = 10;
  totalPosts = 0;
  isLoading = false;

  constructor(
    private userService: UserService,
    private postService: PostService
  ) { }

  ngOnInit(): void {
    console.log("profile components..")
    this.loadUserProfile();
  }

  loadUserProfile(): void {
    this.userService.getCurrentUser().subscribe({
      next: (user) => {
        this.user = user;
        this.loadUserPosts();
      },
      error: (err) => {
        console.error('Error loading user profile:', err);
      }
    });
    
  }

  loadUserPosts(): void {
    debugger;
    this.isLoading = true;
    if (this.user) {
      this.postService.getPostsByUser(this.user.username, this.currentPage, this.pageSize).subscribe({
        next: (data) => {
          this.posts = data.content;
          // this.posts = data.content.sort((a: { createdAt: Date; }, b: { createdAt: Date; })=> new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());
          this.totalPosts = data.totalElements;
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error loading user posts:', err);
          this.isLoading = false;
        }
      });
    }
  }

  onPageChange(event: any): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadUserPosts();
  }
}