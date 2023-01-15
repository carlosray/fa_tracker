db.createUser(
    {
        user: 'fa_user',
        pwd: 'fa_pwd',
        roles: [
            {
                role: 'readWrite',
                db: 'fa_db'
            }
        ]
    }
);