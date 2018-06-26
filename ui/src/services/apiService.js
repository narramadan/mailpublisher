import axios from 'axios'
import * as _ from 'lodash'

// Define API Host
const apiHost = process.env.API_HOST

// Function to trim text under the map
const trimEmails = (emails) => {
  return emails.map((e) => e.trim())
}

// Publish Email
export const publishEmail = (formData) => {
  let emailContent = {
    to: formData.toRecipients,
    cc: formData.ccRecipients,
    bcc: formData.bccRecipients,
    subject: formData.subject,
    body: formData.emailBody
  }

  return axios.post(`${apiHost}/api/publish`, emailContent)
    .then(function (response) {
      console.log("===> Email published successfully",response)
      return Promise.resolve(response.data);
    })
    .catch(function (error) {
      console.log("===> Email publish failed",error.response)
      return Promise.reject(error.response)
    })
}
