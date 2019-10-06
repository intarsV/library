export const API_URL = 'http://localhost:8080';

export const pageRoutes = {
    indexPage:'/',
    loginPage: '/login',
    bookSearch: '/book/search',
    logoutPage: '/logout',
};

export const genres = [
    'POETRY', 'NOVEL', 'PROSE',
];

export const returnedOptions = [
    {key: 'active', value: 'false'},
    {key: 'finished', value: 'true'}
];

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser';

export const userReservation = [
    {data: {handOut: "true", returned: "false"}, optionName: "optionActive", optionHeader: "Active"},
    {data: {handOut: "true", returned: "true"}, optionName: "optionReturned", optionHeader: "History"}
];

export const xxx={data: {handOut: "false", returned: "false"}, optionName: "optionOne", optionHeader: "Queue"};
