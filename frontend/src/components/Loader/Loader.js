import React from "react";
import styled from "styled-components";
import {HashLoader} from "react-spinners";

const Container = styled.div`
    width: 100vw;
    max-width: 100%;
    height: 100vh;
    max-height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
`;

const Loader = () => (
    <Container>
        <HashLoader color={"#b4b4b4"}/>
    </Container>
);

export default Loader;
