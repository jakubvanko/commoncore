import React from "react";
import styled from "styled-components";

const Container = styled.svg`
    position: absolute;
    bottom: -1px;
    left: -5px;
    width: 105vw;
    max-width: 100%;
    padding-top: 100vh;
`;

const BackgroundShape = () => (
    <Container xmlns="http://www.w3.org/2000/svg" viewBox="0 0 2306.13 167.65">
        <path fill="#1b1f3b" d="M0,108.49S496-2.42,1018,0,2306.13,167.65,2306.13,167.65H0Z"/>
    </Container>
);

export default BackgroundShape;
