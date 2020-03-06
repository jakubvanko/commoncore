import React, {useState} from 'react';
import Layout from '@theme/Layout';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import CountUp from 'react-countup';
import {
    HeadingContainer,
    Button,
    SubHeading,
    Heading,
    ButtonContainer,
    LinkContainer,
    SubLink,
    MainFeaturesContainer,
    MainFeature,
    MainFeatureHeading,
    MainFeatureText,
    Section,
    SectionHeading,
    StatsContainer,
    StatsPart,
    StatsDescription,
    SectionHeadingContainer,
    ReviewContainer,
    BottomHeading,
    AdvancedFeaturesContainer,
    AdvancedFeatureHeading,
    AdvancedFeatureText,
    ShellContainer,
    StyledTypist,
    Shell
} from "./CommonCore.styled";
import Arrow from "../../components/Icon/components/Arrow";
import GitHub from "../../components/Icon/components/GitHub";
import Icon from "../../components/Icon/Icon";
import Review from "../../components/Review/Review";
import BackgroundShape from "../../components/BackgroundShape/BackgroundShape";
import Footer from "../../components/Footer/Footer";
import VisibilitySensor from "react-visibility-sensor";

const MAIN_FEATURES = [{
    title: "Material Convertor",
    icon: "ingot",
    description: "Ensures Bukkit material compatibility between different Minecraft versions."
}, {
    title: "Item Builder",
    icon: "repair",
    description: "Implements Builder design pattern to simplify the creation of ItemStacks."
}, {
    title: "Config Loader",
    icon: "gear",
    description: "Loads and creates ItemStacks, Recipes and Inventories directly from the config."
}, {
    title: "Vault Manager",
    icon: "vault",
    description: "Integrates and simplifies the loading and interaction of the Vault plugin API."
},];

const REVIEWS = [{
    author: "Duck",
    text: "This is a brilliant resource. It is easy to set up and adds some VERY interesting and great new additions to the server."
}, {
    author: "Loadedice",
    text: "This plugin is amazing. The author is always keeping it up to date and has even made a recent update to support item attributes. It's a great addition to any server."
}, {
    author: "MG_JerryMouse",
    text: "If I hadn't found this, it would have taken me an hour creating a GUI just for editing beacon effects."
}];

const ADVANCED_FEATURES = [{
    title: "Message Manager",
    icon: "email",
    description: "Creates adaptable strings and simplifies the process of messaging players."
}, {
    title: "Inventory GUI",
    icon: "storage",
    description: "Simplifies the creation of Inventory GUIs by implementing an action-like structure."
}, {
    title: "Sound Convertor",
    icon: "speaker",
    description: "Ensures Bukkit sound compatibility between different Minecraft versions."
}, {
    title: "Reusable Config Structure",
    icon: "dna",
    description: "Ensures a partial compatibility of configuration files across all CommonCore plugins."
}, {
    title: "Complete Documentation",
    icon: "document",
    description: "Includes a documentation and Javadocs for developers and config tutorials for normal users."
}, {
    title: "Custom Items and Textures",
    icon: "plus",
    description: "Implements experimental support for the 3rd party Custom Items and Textures plugin."
},];

const CommonCore = () => {
    const context = useDocusaurusContext();
    const {siteConfig = {}} = context;
    const [areStatsVisible, setStatsVisible] = useState(false);
    return (
        <Layout
            title={`The most flexible Minecraft plugins`}
            description="CommonCore - The most flexible Minecraft plugins">
            <Section>
                <HeadingContainer>
                    <Heading>
                        Common
                    </Heading>
                    <Heading $thin>
                        Core
                    </Heading>
                </HeadingContainer>
                <SubHeading>
                    {siteConfig.tagline}
                </SubHeading>
                <ButtonContainer>
                    <LinkContainer>
                        <Button>
                            <GitHub width={30}/>
                            GitHub
                        </Button>
                        <SubLink to={'docs/01_getting_started'}>
                            Documentation
                            <Arrow width={12}/>
                        </SubLink>
                    </LinkContainer>
                    <Button to={'docs/04_betterbeacons'} $additional>
                        BettterBeacons
                    </Button>
                    <Button to={'docs/05_ultrachest'} $additional>
                        UltraChest
                    </Button>
                </ButtonContainer>
                <ShellContainer>
                    <Shell>
                        <div>$</div>
                        <StyledTypist cursor={{
                            show: true,
                            blink: true,
                            element: '|',
                            hideWhenDone: true,
                            hideWhenDoneDelay: 10,
                        }}>
                            git clone github.com/jakubvanko/commoncore
                        </StyledTypist>
                    </Shell>
                </ShellContainer>
                <MainFeaturesContainer>
                    {MAIN_FEATURES.map(({title, icon, description}) => (
                        <MainFeature>
                            <Icon size={65} circle type={icon}/>
                            <MainFeatureHeading>{title}</MainFeatureHeading>
                            <MainFeatureText>{description}</MainFeatureText>
                        </MainFeature>
                    ))}
                </MainFeaturesContainer>
            </Section>
            <Section $background={"#F3FAFF"}>
                <SectionHeadingContainer>
                    <SectionHeading>
                        <div>The most flexible plugins</div>
                        <div>to satisfy all of your needs</div>
                    </SectionHeading>
                </SectionHeadingContainer>
                <SubHeading>
                    <div>You do not have to be a programmer to fully customize CommonCore plugins.</div>
                    <div>The configuration is simple and straightforward.</div>
                </SubHeading>
                <VisibilitySensor onChange={isVisible => {
                    if (isVisible && !areStatsVisible) {
                        setStatsVisible(true)
                    }
                }}>
                    <StatsContainer>
                        <StatsPart>
                            <SectionHeading $color={"#2196F3"}>
                                {areStatsVisible ?
                                <CountUp
                                    start={1000}
                                    duration={2}
                                    easingFn={function (t, b, c, d) {
                                        return c * ((t = t / d - 1) * t * t * t * t + 1) + b;
                                    }}
                                    end={10000}
                                    suffix="+"/> : <span>1000</span>
                                }
                            </SectionHeading>
                            <StatsDescription>
                                CommonCore plugin downloads
                            </StatsDescription>
                        </StatsPart>
                        <StatsPart>
                            <SectionHeading $color={"#2196F3"}>
                                {areStatsVisible ?
                                <CountUp
                                    start={1}
                                    duration={2.6}
                                    easingFn={function (t, b, c, d) {
                                        return c * ((t = t / d - 1) * t * t * t * t + 1) + b;
                                    }}
                                    end={100}
                                    suffix="+"/> : <span>1</span>
                                }
                            </SectionHeading>
                            <StatsDescription>
                                Servers currently running CC plugins
                            </StatsDescription>
                        </StatsPart>
                    </StatsContainer>
                </VisibilitySensor>
                <ReviewContainer>
                    {REVIEWS.map(({author, text}) => (
                        <Review author={author} text={text}/>
                    ))}
                </ReviewContainer>
                <BackgroundShape/>
            </Section>
            <Section $background={"#1B1F3B"}>
                <BottomHeading>
                    More advanced features
                </BottomHeading>
                <AdvancedFeaturesContainer>
                    {ADVANCED_FEATURES.map(({title, icon, description}) => (
                        <MainFeature>
                            <Icon size={25} type={icon}/>
                            <AdvancedFeatureHeading>{title}</AdvancedFeatureHeading>
                            <AdvancedFeatureText>{description}</AdvancedFeatureText>
                        </MainFeature>
                    ))}
                </AdvancedFeaturesContainer>
            </Section>
            <Footer/>
        </Layout>
    );
};

export default CommonCore;
