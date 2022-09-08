#!/usr/bin/env bash
export NUMBER=$(curl --location --request POST 'https://api.github.com/repos/eduardomallmann/viacep-proxy/pulls' \
--header 'Accept: Accept: application/vnd.github+json' \
--header 'Authorization: Bearer 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' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "Merging changes",
    "body": "Merging changes",
    "head": "eduardomallmann:feature/cicd2",
    "base": "main"
}' | jq .number)

curl --location --request PUT 'https://api.github.com/repos/eduardomallmann/viacep-proxy/pulls/$NUMBER/merge' \
--header 'Accept: application/vnd.github+json' \
--header 'Authorization: Bearer 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'