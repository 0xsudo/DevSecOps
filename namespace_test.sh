#!/bin/bash
NAMESPACE = devsecops

if ! kubectl get namespace devsecops > /dev/null 2>&1; then
    kubectl create namespace $NAMESPACE
else
    kubectl delete namespace $NAMESPACE
fi

#add execute permissions by running sudo chmod +x 'thisfilename'