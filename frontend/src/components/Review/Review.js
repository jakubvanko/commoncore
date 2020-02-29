import React from "react";

import {Container, Quote, Text, AuthorText} from "./Review.styled"

const Review = ({text, author}) => (
    <Container>
        <Quote $top>“</Quote>
        <Text>{text}</Text>
        <AuthorText>~ {author}</AuthorText>
        <Quote>„</Quote>
    </Container>
);

export default Review;
