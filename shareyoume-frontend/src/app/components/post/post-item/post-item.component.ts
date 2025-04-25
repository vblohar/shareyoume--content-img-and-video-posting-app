import { Component, Inject, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Post } from '../../models/post.model';
import { CommonModule, DatePipe } from '@angular/common';
import { PostService } from '../../services/post.service';

@Component({
  selector: 'app-post-item',
  imports: [DatePipe, CommonModule],
  templateUrl: './post-item.component.html',
  styleUrl: './post-item.component.css'
})

export class PostItemComponent {

 
  @Input() post!: Post;

  //postService = Inject(PostService);
  imageUrl: string | undefined;

  ngOnChanges(changes: SimpleChanges): void {
    console.log("in ngonchanges....", changes)
    
    if (this.post?.mediaName) {

      this.postService.getMediaFile(this.post.mediaName).subscribe((blob: Blob | MediaSource) => {
        this.imageUrl = URL.createObjectURL(blob);
        console.log("Image", this.imageUrl);
      });
    }
  }


  constructor(private postService: PostService) {

  }

  // fetchImage(): any {
    
  //   if (this.post?.mediaName) {

  //     this.postService.getMediaFile(this.post.mediaName).subscribe((blob: Blob | MediaSource) => {
  //       console.log(URL.createObjectURL(blob));
  //     });
  //   }
  // }
  
}