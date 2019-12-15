export const API_URL = 'http://193.0.140.12:80';

export const adminMenu = [
    {key: 'Main', value: '/admin'},
    {key: 'Manage authors', value: '/admin/authors'},
    {key: 'Manage books', value: '/admin/books'},
    {key: 'Search reservations', value: '/admin/reservations'},
    {key: 'Users queue', value: '/admin/queue'},
    {key: 'Manage users', value: '/admin/users'}
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
    {key: 'active', value: 'HANDOUT'},
    {key: 'finished', value: 'RETURNED'}
];

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser';

export const userReservation = [
    {data:'HANDOUT', optionName: "optionActive", optionHeader: "Active"},
    {data: 'RETURNED', optionName: "optionReturned", optionHeader: "History"}
];

//fieldList for forms
export const authorFieldList = [{id: 'authorName', type: 'text'}];
export const bookFieldList = [{id: 'bookTitle', type: 'text'}, {id: 'author', type: 'text'},
                                {id: 'genre', type: 'text'}, {id: 'copies', type: 'number'}];
export const registrationFieldList = [{id: 'userName', type: 'reg-login'}, {id: 'password', type: 'reg-password'}];
