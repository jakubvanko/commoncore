import styled from "styled-components";
import Link from '@docusaurus/Link';
import Typist from 'react-typist';

export const HeadingContainer = styled.div`
    display: flex;
    width: 100%;
    justify-content: center;
    padding: 4rem 0 1.3rem 0;
`;

const BaseHeading = styled.h1`
    font-size: 2.8rem;
    text-align: center;
`;

export const Heading = styled(BaseHeading)`
    text-transform: uppercase;
    font-weight: ${props => props.$thin ? "400" : "bold"};
    text-align: center;
    letter-spacing: 0.2em;
`;

export const SubHeading = styled.h2`
    color: #5B5F75;
    font-weight: 400;
    text-align: center;
    line-height: 1.5em;
`;

export const ButtonContainer = styled.div`
    display: flex;
    width: 100%;
    justify-content: space-evenly;
    padding: 2.2rem 10vw;
`;

export const Button = styled(Link)`
    border-radius: 6px;
    background-color: ${props => props.$additional ? "#1B1F3B" : "#2196F3"};
    width: 8.6em;
    height: 3.4em;
    font-size: 1.15rem;
    font-weight: bold;
    color: white !important;
    border: none;
    outline: none;
    cursor: pointer;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: center;
    
    svg {
        margin-right: 0.8rem;
    }
`;

export const LinkContainer = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
`;

export const SubLink = styled(Link)`
    color: #2196F3 !important;
    font-weight: 600;
    display: flex;
    align-items: center;
    margin: 0.6rem 0;
    svg {
        margin-left: 0.4em;
    }
`;

export const MainFeaturesContainer = styled.div`
    display: grid;
    grid-template-columns: auto auto;
    row-gap: 4rem;
    margin: 4.5rem 5vw 5rem 5vw;
    justify-content: space-between;
`;

export const AdvancedFeaturesContainer = styled.div`
    display: grid;
    grid-template-columns: auto auto auto;
    row-gap: 2.5rem;
    justify-content: space-between;
    padding: 3.2rem 0 3.2rem 0;
    margin: 0 3vw;
`;

export const MainFeature = styled.div`
    display: grid;
    grid-template-columns: min-content max-content;
    grid-template-rows: min-content max-content;
    grid-template-areas: "icon heading" "icon description";
    grid-column-gap: 1.2vw;
`;

export const MainFeatureHeading = styled.h1`
    font-weight: 600;
    font-size: 1.6rem;
    grid-area: heading;
`;

export const MainFeatureText = styled.h2`
    color: #5B5F75;
    font-weight: 400;
    font-size: 1.4rem;
    grid-area: description;
    max-width: 20vw;
    word-wrap: break-word;
    line-height: 1.4em;
`;

export const AdvancedFeatureText = styled(MainFeatureText)`
    color: white;
    font-size: 1rem;
    line-height: normal;
    max-width: 15vw;
`;

export const Section = styled.section`
    background-color: ${props => props.$background || "white"};
    padding: 0 15vw;
    position: relative;
`;

export const SectionHeadingContainer = styled.div`
    padding: 10vh 10vw 3.3vh 10vw;
`;

const BaseSectionHeading = styled(BaseHeading)`
    font-weight: 600;
    line-height: 1.4em;
`;

export const SectionHeading = styled(BaseSectionHeading)`
    color: ${props => props.$color || "black"}
`;

export const BottomHeading = styled(BaseSectionHeading)`
    color: white;
    text-align: left;
    padding: 0 3vw;
`;

export const StatsPart = styled.div`
    display: flex;
    align-items: center;
    flex-direction: column;
`;

export const StatsContainer = styled.div`
    display: flex;
    justify-content: space-evenly;
    margin: 0 5vw;
    padding-top: 1.6rem;
`;

export const StatsDescription = styled.h3`
    font-weight: 600;
`;

export const AdvancedFeatureHeading = styled(StatsDescription)`
    color: white;
`;

export const ReviewContainer = styled.div`
    display: flex;
    justify-content: space-evenly;
    margin: 4rem 2rem 0 2rem;
    padding-bottom: 17rem;
`;

export const ShellContainer = styled.div`
    display: flex;
    justify-content: center;
    padding-top: 0.5rem;
`;

export const Shell = styled.div`
    background-color: #1B1F3B;
    display: flex;
    align-content: center;
    color: #ff1744;
    padding: 2rem 3rem;
    border-radius: 10px;
    width: 50vw;
    letter-spacing: 0.1em;
`;

export const StyledTypist = styled(Typist)`
    color: white;
    padding-left: 1.8rem;
`;
