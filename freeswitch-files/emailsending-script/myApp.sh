#!/bin/bash
#sudo mkdir /root/Desktop/MYAPPPPPPPPPPPPPPPPPPPPP 2> /root/Desktop/log.txt
#touch /tmp/xyz.txt

#!/bin/bash

# Define the URL and API key
url="https://api.brevo.com/v3/smtp/email"
api_key="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"

# Define the JSON data
json_data='{
   "sender": {
      "name": "Zahid",
      "email": "zahidsaleem0440@gmail.com"
   },
   "to": [
      {
         "email": "zahidsaleem0440@gmail.com",
         "name": "Zahid"
      }
   ],
   "subject": "Hello EMAIL SENDING TEST",
   "htmlContent": "<html><head></head><body><p>Hello,</p>This is my first transactional email sent from Brevo.</p></body></html>"
}'

# Send the request using curl
response=$(curl --location "$url" \
  --header 'accept: application/json' \
  --header "api-key: $api_key" \
  --header 'content-type: application/json' \
  --header 'Cookie: __cf_bm=Ep.4k8VzfDncoXFmg.QiiKvQCrjCT61BC6Xh7Na2PII-1699186867-0-AYbOkoKdKZo/GbiR/1kMR3dkdJg4pBciJi6DP4UqNSGIDhhaiWMRQLgDiPqqlBaZpGmRl4q/VS4xp3I1fyaI+x8=' \
  -d "$json_data")

# Display the response
echo "Response:"
echo "$response"

echo "Email sent Successfully" 
