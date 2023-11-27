import { User } from "../../me/interface/user.interface";

export interface PostComment {
    id: string;
    date: Date;
    content: string;
    user: User;
  }