import { Component, OnInit } from '@angular/core';
import { Post } from '../models/post.model';
import { User } from '../models/user.model';
import { PostService } from '../services/post.service';
import { UserService } from '../services/user.service';
import { CommonModule } from '@angular/common';
import { PostListComponent } from "../post/post-list/post-list.component";
import {MatPaginatorModule} from '@angular/material/paginator';

@Component({
  selector: 'app-profile',
  imports: [CommonModule, PostListComponent, MatPaginatorModule],
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
    this.loadUserProfile();
    this.loadUserPosts();
  }

  loadUserProfile(): void {
    this.userService.getCurrentUser().subscribe({
      next: (user) => {
        this.user = user;
      },
      error: (err) => {
        console.error('Error loading user profile:', err);
      }
    });
  }

  loadUserPosts(): void {
    this.isLoading = true;
    if (this.user) {
      this.postService.getPostsByUser(this.user.username, this.currentPage, this.pageSize).subscribe({
        next: (data) => {
          this.posts = data.content;
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