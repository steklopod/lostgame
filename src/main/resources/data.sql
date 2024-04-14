INSERT INTO user_balance (user_id, user_nick, amount, denomination, max_win, currency, jp_key, lock_version)
VALUES ('f8bf345c-584a-40e3-b3c3-a72e9ce8ac7b', 'GamerTwo', 20000, 1, 10000, 'EUR', 'jackpot-group-2', 1),
       ('c3eb0de1-65ba-434f-b2aa-8912b76870ae', 'PlayerOne', 5000, 2, 20000, 'USD', null, 2);

INSERT INTO user_session(session_id, user_id)
VALUES ('c3eb0de1-65ba-434f-b2aa-8912b76870ae', 'c3eb0de1-65ba-434f-b2aa-8912b76870ae'),
       ('f8bf345c-584a-40e3-b3c3-a72e9ce8ac7b', 'f8bf345c-584a-40e3-b3c3-a72e9ce8ac7b');
