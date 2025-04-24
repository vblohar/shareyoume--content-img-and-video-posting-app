import { Component, Input } from '@angular/core';
import { Post } from '../../models/post.model';
import { PostItemComponent } from "../post-item/post-item.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-post-list',
  imports: [PostItemComponent, CommonModule],
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.css'
})

export class PostListComponent {
  @Input() posts: Post[] = [];
}