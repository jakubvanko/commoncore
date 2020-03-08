import React from "react";
import styled from "styled-components";

const Container = styled.svg`
    position: absolute;
    bottom: -1px;
    left: -5px;
    padding-top: 110vh;
`;

const BackgroundShape = () => (
    <Container xmlns="http://www.w3.org/2000/svg" width="100vw" viewBox="0 0 2306.13 167.65">
        <path fill="#1b1f3b" d="M0,108.49S496-2.42,1018,0,2306.13,167.65,2306.13,167.65H0Z"/>
    </Container>
);

export default BackgroundShape;
