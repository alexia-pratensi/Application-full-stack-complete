import { User } from "../../me/interface/user.interface";
import { PostComment } from "./post-comment.interface";
import { Topic } from "../../topic/interface/topic.interface";

export interface Post {
    id: number;
    title: string;
    date: Date;
    user: User;
    content: string;
    topic: Topic;
    comments: PostComment[];
    createdAt: Date;
    updatedAt?: Date;
}