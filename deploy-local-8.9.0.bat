SET ODM_USER_CREDS_USR=resAdmin
SET ODM_USER_CREDS_PSW=resAdmin
SET JRULES_ANT_LIB=../su-rules-deploy-image/ant-lib-8.9.0
chcp 1251
ant -DODM_HOST=127.0.0.1 -DODM_PORT=9090 -f deploy.xml deploy-surules