module.exports = {
    title: 'CommonCore',
    tagline: 'A sensible Bukkit library to bootstrap you Minecraft Plugins',
    url: 'https://your-docusaurus-test-site.com',
    baseUrl: '/',
    favicon: 'img/favicon.ico',
    organizationName: 'jakubvanko', // Usually your GitHub org/user name.
    projectName: 'commoncore', // Usually your repo name.
    themeConfig: {
        disableDarkMode: true,
        navbar: {
            title: 'CommonCore',
            logo: {
                alt: 'CommonCore logo',
                src: 'img/logo.svg',
            },
            links: [
                {to: 'docs/doc1', label: 'Docs', position: 'right'},
                {
                    href: 'https://github.com/facebook/docusaurus',
                    label: 'GitHub',
                    position: 'right',
                },
            ],
        }
    },
    presets: [
        [
            '@docusaurus/preset-classic',
            {
                docs: {
                    sidebarPath: require.resolve('./sidebars.js'),
                    editUrl:
                        'https://github.com/facebook/docusaurus/edit/master/website/',
                },
                theme: {
                    customCss: require.resolve('./src/css/custom.css')
                },
            },
        ],
    ],
};
