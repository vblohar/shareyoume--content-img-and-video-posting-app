import { Component, Input } from '@angular/core';
import { Post } from '../../models/post.model';
import { CommonModule, DatePipe } from '@angular/common';

@Component({
  selector: 'app-post-item',
  imports: [DatePipe, CommonModule],
  templateUrl: './post-item.component.html',
  styleUrl: './post-item.component.css'
})

export class PostItemComponent {
  @Input() post!: Post;
}