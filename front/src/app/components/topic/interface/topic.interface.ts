import { Post } from "../../post/interface/post.interface";

export interface Topic {
    id: number;
    title: string;
    description: string;
    date: Date;
    posts: Post[];
  }
  