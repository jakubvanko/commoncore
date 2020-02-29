import React from "react";
import styled from "styled-components";
import Arrow from "./components/Arrow";
import Dna from "./components/Dna";
import Document from "./components/Document";
import Email from "./components/Email";
import Gear from "./components/Gear";
import GitHub from "./components/GitHub";
import Ingot from "./components/Ingot";
import Plus from "./components/Plus";
import Repair from "./components/Repair";
import Speaker from "./components/Speaker";
import Storage from "./components/Storage";
import Vault from "./components/Vault";

const IconCircle = styled.div`
    background-color: rgba(33, 150, 243, 0.1);
    width: ${props => props.$size}px;
    height: ${props => props.$size}px;
    border-radius: ${props => props.$size}px;
    display: flex;
    justify-content: center;
    align-items: center;
`;

const IconContainer = styled.div`
    grid-area: icon;
`;

const ICON_TYPES = {
    arrow: Arrow,
    dna: Dna,
    document: Document,
    email: Email,
    gear: Gear,
    github: GitHub,
    ingot: Ingot,
    plus: Plus,
    repair: Repair,
    speaker: Speaker,
    storage: Storage,
    vault: Vault
};

const Icon = ({type, circle, size}) => {
    const IconComponent = ICON_TYPES[type];
    if (circle) {
        return (
            <IconContainer>
                <IconCircle $size={size}>
                    <IconComponent width={size / 3 * 2}/>
                </IconCircle>
            </IconContainer>
        )
    }
    return (
        <IconContainer>
            <IconComponent width={size}/>
        </IconContainer>
    )
};

export default Icon;
