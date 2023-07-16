### Todo
- **Controllers**
  - **AuthController**
    - Восстановление пароля
    - **/registration POST**: отправка email-сообщения с кодом регистрации;
    - **/login POST**: отправка email-сообщения с кодом регистрации;

### In Progress
- **Services**
  - **RoleService**
    - **addNewRoleFromUser**: добавление новой роли существующему пользователю;
- **Controllers**
  - **RoleController**
    - **/user PUT** добавление новой роли существующему пользователю;
  - **AuthController**
    - **/registration POST**: отправка смс-сообщения с кодом регистрации (messente с пакетом из maven или smsaero);
    - **/login POST**: отправка смс-сообщения с кодом регистрации (messente с пакетом из maven или smsaero);
- **Entities**
- **Enums**
- **Dto**
- **Exceptions**

### Done ✓
- **Controllers**
  - **AuthController**
    - **/info GET**: получение реального имени пользователя по уникальному имени, электронной почте, или номеру телефона. 
    Используется при авторизации пользователя, на первом экране. Подразумевается, что, если пользователь существует, 
    он будет направлен на страницу ввода пароля. при этом, если запрос был отправлен с известного устройства, контроллер 
    реальное имя пользователя, для использования его в тексте приветствия, если нет, уникальное имя, использованное при 
    отправке запроса. Если пользователя не существует, будет возвращён код 404 и перенаправление на страницу регистрации;
    - **/registration POST**: регистрация нового пользователя;
    - **/login POST**: авторизация существующего пользователя;
  - **SettingsController**
    - **/visit POST**: обновление даты последнего посещения. Отслеживается, какой тип профиля был выбран пользователем 
    после регистрации или авторизации (реальный пользователь или псевдоним, в качестве которого используется уникальное 
    поле псевдонима, в текущей реализации, необязательно, отличное от уникального имени пользователя);
    
- **Services**
  - **JwtService**
    - **extractUsername**: извлечение имени пользователя из токена;
    - **generateToken**: генерация новой пары токенов. Токен доступа генериется на срок в 24 часа, а токен обновления 
    на срок в 1 неделю;
    - **isTokenValid**: является ли токен валидным. Осуществляется проверка на соответствие имени пользователя, 
    извлечённого из токена и имени пользователя, полученного из БД, а так же, проверяется, не просрочен ли токен;
    - **isAccessToken**: является ли токен, токеном доступа;
  - **UserService**
    - **getDisplayName**: получение реального имени пользователя по уникальному имени, электронной почте, или номеру 
    телефона. Если искомое значение начинается со знака **+**, осуществляется поиск по номеру телефона, 
    если оно содержит **@**, поиск осуществляется по адресу электронной почты, в любом ином случае, поиск осуществляется 
    по уникальному имени пользователя. Если пользователь не найден, будет возвращено исключение NotFoundException;
    - **signUp**: регистрация нового пользователя. При регистрации пользователю устанавливается роль ROLE_USER и 
    устанавливается временная зона, из которой был осуществлён запрос (будет использоваться для оптимизации времени 
    отправки уведомлений от сервера, а так же, в дальнейшем, для аналитики). Происходит создание пользователя, регистрация 
    устройства создание токенов доступа и обновления и их возвращение;
    - **signIn**: авторизация существующего пользователя. После нахождения пользователя, так же, как и при регистрации
    пользователя, производится обновление устройства с которого осуществлён доступ, обновление токенов доступа, а так же
    их возрат после обновления временной зоны, из которой была осуществлён последний доступ;
    - **updateVisit**: обновление даты последнего посещения. Вызов сервиса обновления даты последнего посещения;
  - **VisitsService**
    - **updateVisit**: обновление даты последнего посещения. Устанавливается текущее время по UTC. Происходит вызов 
    метода сохраниения в репощитории;
  - **DeviceService**
    - **updateDevice**: обновление информации о используемом пользователем устройстве. Если устройство используется 
    другим пользователем, будет выброшено исключение AlreadyExistException, которое впоследствии будет обработано
    контроллером, осуществившим вызов функции. Иначе будет возвращена информация о зарегистрированном устройстве;
    - **checkDevice**: поиск утройства в репозитории. Возвращет сущность устройства или null, который необходимо
    отлавливать выше по цепочке вызовов;
  - **TokensService**
    - **updateTokens**: обновление токенов доступа. Осуществляется поиск токенов в репозитории по идентификатору
    устройства. В случае, если токен найден, но идентификатор пользователя не соответствует идентификатору пользователя
    осуществившего запрос, выбрасывается исключение AlreadyExistException, что устройство уже используется другим 
    пользователем, иначе, если токен не найден, создаётся новая пара токенов, если найден, обновляется существующая;
  - **LocalizedResponseMessageService**
    - **find**: поиск локализованного сообщения по ключу и локали. Если сообщение найдено в БД, возвращается текст
    сообщения из БД, иначе, возвращается дефолтное сообщение ключа (enum), использованного для поиска;
    
- **Repositories**
  - **UserRepository**
    - **findByUsername**: стандартная функция поиска пользователя по уникальному имени пользователя;
    - **findByPhoneNumber**: стандартная функция поиска пользователя по номеру телефона;
    - **findByEmail**: стандартная функция поиска пользователя по адресу электронной почты;
  - **VisitsRepository**
    - **findByUserAndVisitedWithAlias**: стандартная функция поиска записи по пользователю и типу авторизации;
  - **DeviceRepository**
    - **findByDeviceId**: стандартная функция поиска устройства по идентификатору устройства;
  - **RoleRepository**
    - **findByName**: стандартная функция поиска роли по имени роли;
  - **TokensRepository**
    - **findByDeviceId**: стандартная функция поиска токена по идентификатору устройства;
  - **LocalizedResponseMessageRepository**
    - **findByKeyAndLocale**: стандартная функция поиска локализованного сообщения по уникальному ключу и локали;
    
- **Configs**
  - **SecurityConfig**
    - **уровни доступа**
      - **PATH_AUTH**: полный доступ;
      - **PATH_ADMIN**: доступ только для пользователей с ролью ROLE_ADMIN или ROLE_OWNER. Администрирование;
      - **PATH_MANAGE**: доступ только для пользователей с ролью ROLE_MANAGER или ROLE_OWNER;
      - **PATH_ROLES**: доступ только для пользователей с ролью ROLE_OWNER. Используется для обновления роли пользователя;
      - **anyRequest**: доступ только для авторизованных пользователей;
    - **фильтра**: результаты фильтрации, содержащие ошибки, проходят через FilterHelper, содержащий методы error, 
    forbidden, notFound, в которых производится оборачивание ответа сервера в кастомизированный ResponseWrapper 
    состоящий из полей data и message;
      - **LocaleHeaderFilter**: первый фильтр. Отфильтровывает все запросы, в заголовке которых не указана локаль;
      - **JwtAuthenticationFilter**: следует за LocaleHeaderFilter. Не затрагивает запросы по пути PATH_AUTH. Фильтрует
      запросы, если пользователь извлечённый из токена на найден, либо, роль не соответствует уровню доступа, либо, 
      если токен, использованный для получения доступа, невалиден;
      - **TimeZoneHeaderFilter**: следует за JwtAuthenticationFilter. Затрагивает только запросы по пути PATH_AUTH_LOGIN
      и PATH_AUTH_REGISTRATION, пропуская остальные. Фильтрует запрос, если в заголовке нет информации о временной зоне;