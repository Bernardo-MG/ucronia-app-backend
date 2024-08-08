#!/bin/bash
GRAFANA_URL="http://admin:admin@grafana:3000"
DASHBOARD_ID="14430" # Replace with the actual dashboard ID
DASHBOARD_UID="spring_boot_stats" # Replace with the desired UID
FOLDER_ID=0 # 0 for the general folder

curl -s "${GRAFANA_URL}/api/dashboards/uid/${DASHBOARD_UID}" | grep '"message":"Not found"' > /dev/null
if [ $? -eq 0 ]; then
    DASHBOARD_JSON=$(curl -s "https://grafana.com/api/dashboards/${DASHBOARD_ID}/revisions/latest/download")
    curl -s -X POST -H "Content-Type: application/json" -d "{\"dashboard\":${DASHBOARD_JSON},\"overwrite\":true,\"folderId\":${FOLDER_ID}}" "${GRAFANA_URL}/api/dashboards/db"
fi
