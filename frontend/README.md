# **CommonCore Website**

This folder contains code for the website of CommonCore plugins. You can visit it at [commoncore.jakubvanko.com](https://commoncore.jakubvanko.com/).

#### Table of Contents

- [Introduction](#introduction)
- [Design](#design)
- [Features](#features)
- [Used Technologies](#used-technologies)
- [Deployment](#deployment)
- [Conclusion](#conclusion)

## Introduction

Documentation is a vital part of software development. A good quality website and documentation helps possible users decide whether they should invest their time and resources into further development of the project. This website serves the mentioned purpose for CommonCore plugins.

The website structure was created using Docusaurus 2. However, the landing page was rewritten from scratch to account for a custom design.

##### Goal of this project
The goal of this project was to create a professional lightweight front-end web application that would serve as a landing page for CommonCore plugins and its documentation. It had to showcase the features of CommonCore while offering a simple navigation regarding its documentation.


## Design

The design is simple and straightforward. It is quite minimalistic and offers a quick loading time and a few pleasant animations.


## Features

##### Front-End
- Responsive design
- SVG shape background image
- Custom components
- Documentation section


## Used Technologies:

##### Front-End
- Docusaurus 2
- React.js v16.8
  - React Hooks
  - React Lazy and Suspense (not supported in Docusaurus 2 yet)
- styled-components
- react-visibility-sensor

##### Other technologies
- Adobe XD
- Adobe Illustrator


## Deployment

The project was deployed on a DigitalOcean droplet where it is being managed by Nginx.

The deployment of frontend can be done simply using a node.js script ("npm run deploy").


## Conclusion

The project was completed successfully. All needed features were implemented while ensuring the simplicity of possible further development.
