import styled from "styled-components";

export const Container = styled.div`
    background-color: white;
    border-radius: 10px;
    box-shadow: 0 3px 10px #0000005E;
    position: relative;
    width: 18vw;
`;

export const Quote = styled.div`
    position: absolute;
    color: #2196F3;
    font-size: 2rem;
    font-weight: bolder;
    top: ${props => props.$top && "15px"};
    left: ${props => props.$top && "20px"};
    bottom: ${props => !props.$bottom && "15px"};
    right: ${props => !props.$bottom && "20px"};
`;

const BaseText = styled.p`
    font-size: 1.2rem;
    color: #5B5F75;
`;

export const Text = styled(BaseText)`
    margin: 45px 30px 95px 30px;
    text-align: center;
`;

export const AuthorText = styled(BaseText)`
    font-weight: 600;
    text-align: right;
    position: absolute;
    bottom: ${props => !props.$bottom && "15px"};
    right: ${props => !props.$bottom && "40px"};
`;
