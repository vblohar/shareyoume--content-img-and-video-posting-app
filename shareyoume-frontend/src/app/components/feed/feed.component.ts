import { Component, OnInit } from '@angular/core';
import { Post } from '../models/post.model';
import { PostService } from '../services/post.service';
import { CreatePostComponent } from "../post/create-post/create-post.component";
import { PostListComponent } from "../post/post-list/post-list.component";
import {MatPaginatorModule} from '@angular/material/paginator';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-feed',
  imports: [CreatePostComponent, PostListComponent, MatPaginatorModule, CommonModule],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.css'
})

export class FeedComponent implements OnInit {
  posts: Post[] = [];
  currentPage = 0;
  pageSize = 10;
  totalPosts = 0;
  isLoading = false;

  constructor(private postService: PostService) { }

  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts(): void {
    this.isLoading = true;
    this.postService.getAllPosts(this.currentPage, this.pageSize).subscribe({
      next: (data) => {
        this.posts = data.content;
        this.totalPosts = data.totalElements;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error loading posts:', err);
        this.isLoading = false;
      }
    });
  }

  onPageChange(event: any): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadPosts();
  }
}
