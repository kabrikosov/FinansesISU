export const strings = {
    "en": {
        model: {
            name: "Name",
            price: "Price",
            quantity: "Quantity",
            date: "Date",
            category: "Category",
            subscription: "Subscription",
            listOfProducts: "List of products",
            startDate: "Start date",
            expirationDate: "Expiration date",
            parentCategory: "Parent category",
        },
        forms: {
            submit: "Submit",
            selectParent: "Select parent category",
            delete: "Delete"
        },
        home: "Home",
        language: "Language",
        products: "Products",
        categories: "Categories",
        subscriptions: "Subscriptions",
        isu: "ISU",
        add: "Add",
        error: {
            startDateTime: {
                empty: "Start date time must not be empty.",
            },
            name: {
                empty: "Name must not be empty.",
            },
            price: {
                empty: "Price must not be empty.",
                negative: "Price can not be less than zero.",
            },
            date: {
                empty: "Date must not be empty."
            },
            category: {
                null: "Category can not be null.",
            },
            expirationDateTime: {
                beforeStart: "Expiration date and time can not be before start date and time.",
            },
            quantity: {
                lessOrEqualsZero: "Quantity can not be less or equal zero."
            }
        },
        chooseOption: "Choose from list",
    },
    "ru": {
        model: {
            name: "Название",
            price: "Цена",
            quantity: "Количество",
            date: "Дата",
            category: "Категория",
            subscription: "Подписка",
            listOfProducts: "Список продуктов",
            startDate: "Начало действия подписки",
            expirationDate: "Окончание действия подписки",
            parentCategory: "Родительская категория",
        },
        forms: {
            submit: "Отправить",
            selectParent: "Выберите родительскую категорию",
            delete: "Удалить"
        },
        home: "Главная",
        language: "Язык",
        products: "Продукты",
        categories: "Категории",
        subscriptions: "Подписки",
        isu: "ИГУ",
        add: "Добавить",
        error: {
            startDateTime: {
                empty: "Начало действия подписки не может быть пустым.",
            },
            name: {
                empty: "Имя не может быть пустым.",
            },
            price: {
                empty: "Стоимость не может быть пустой.",
                negative: "Стоимость не может быть меньше нуля.",
            },
            date: {
                empty: "Дата не может быть пустой."
            },
            category: {
                null: "Категория не может быть пустой.",
            },
            expirationDateTime: {
                beforeStart: "Окончание срока действия подписки не может быть раньше начала.",
            },
            quantity: {
                lessOrEqualsZero: "Количество не может быть меньше или равно нулю."
            }
        },
        chooseOption: "Выберите из списка",
    }
}