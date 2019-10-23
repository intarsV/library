export const API_URL = 'http://localhost:8080';

export const pageRoutes = {
    indexPage:'/',
    loginPage: '/login',
    bookSearch: '/book/search',
    logoutPage: '/logout',
};

export const adminMenu = [
    {key: 'Main', value: '/admin'},
    {key: 'Manage authors', value: '/admin/add/author'},
    {key: 'Manage books', value: '/admin/add/book'},
    {key: 'Manage users', value: '/admin/users'},
    {key: 'Search reservations', value: '/admin/reservations'},
    {key: 'Users queue', value: '/admin/queue'}
];

export const userMenu = [
    {key: 'Main', value: '/user'},
    {key: 'My reservations', value: '/user/reservations'},
    {key: 'Search book', value: '/user/search'}
];

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
