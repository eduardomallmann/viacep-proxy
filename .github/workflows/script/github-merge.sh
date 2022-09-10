#!/usr/bin/env bash
curl --location --request POST 'https://api.github.com/repos/eduardomallmann/viacep-proxy/pulls' \
--header 'Accept: Accept: application/vnd.github+json' \
--header 'Authorization: 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' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "Merging changes",
    "body": "Merging changes",
    "head": "eduardomallmann:feature/cicd2",
    "base": "main"
}'
export NUMBER=$(curl --location --request GET 'https://api.github.com/repos/eduardomallmann/viacep-proxy/pulls?state=open&head=eduardomallmann:feature/cicd2' \
--header 'Cookie: _octo=GH1.1.1594346806.1653414017; logged_in=no' | jq '.[0].number')
curl --location --request PUT 'https://api.github.com/repos/eduardomallmann/viacep-proxy/pulls/$NUMBER/merge' \
--header 'Accept: application/vnd.github+json' \
--header 'Authorization: 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'