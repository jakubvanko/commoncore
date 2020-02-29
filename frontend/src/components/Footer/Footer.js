import React from "react";
import styled from "styled-components";
import Link from '@docusaurus/Link';

const Container = styled.footer`
    background-color: #1B1F3B;
    width: 100vw;
    max-width: 100%;
    padding-bottom: 1.5rem;
`;

const LinkContainer = styled.div`
    display: flex;
    justify-content: center;
    
    @media (max-width: 700px) {
        display: grid;
        grid-template-columns: auto;
        padding-bottom: 1rem;
    }
`;

const StyledLink = styled(Link)`
    color: rgba(255,255,255,0.5) !important;
    cursor: pointer;
    font-weight: 600;
    margin: 2rem 1.5rem;
    
    :hover {
        color: white !important;
    }
    
    @media (max-width: 700px) {
        text-align: center;
        margin: 0.5rem 1.5rem;
    }
`;

const Copyright = styled.p`
    color: rgba(255,255,255,0.5);
    text-align: center;
    margin: 0;
    line-height: 2em;
    padding: 0 10vw;
`;

const Footer = () => (
    <Container>
        <LinkContainer>
            <StyledLink href={"https://github.com/jakubvanko/commoncore"} target={"_blank"}
                        rel={"noopener noreferrer"}>GitHub</StyledLink>
            <StyledLink to={"docs/doc1"}>Documentation</StyledLink>
            <StyledLink>BetterBeacons</StyledLink>
            <StyledLink>UltraChest</StyledLink>
            <StyledLink>License</StyledLink>
        </LinkContainer>
        <Copyright>
            <div>This work is dedicated to the public domain. Rights for parts of the web design go to their respective
                owners.
            </div>
            <div>Copyright &copy; 2020, Jakub Vanko.</div>
        </Copyright>
    </Container>
);

export default Footer;
