import json
import os
import subprocess


def validate_task_twenty_seven(result=None, device_id=None, backup_dir=None):
    """验证任务二十七：新增一个配送地址：收件人'Alex Johnson'，手机号'3105550199'，地址'123 Main St, Apt 8C, Los Angeles, California'，编码'90012'，并设为默认地址。"""
    addresses_file_path = os.path.join(backup_dir, "addresses.json") if backup_dir else "addresses.json"

    cmd = ["adb"]
    if device_id:
        cmd.extend(["-s", device_id])
    cmd.extend(["exec-out", "run-as", "com.example.amazon_sim", "cat", "files/addresses.json"])
    subprocess.run(cmd, stdout=open(addresses_file_path, "w"))

    try:
        with open(addresses_file_path, "r", encoding="utf-8") as f:
            data = json.load(f)
            addresses = data if isinstance(data, list) else []
    except:
        return False

    if not addresses:
        return False

    last_address = addresses[-1]
    if not isinstance(last_address, dict):
        return False

    required_fields = ["id", "fullName", "phoneNumber", "city", "state", "zipCode", "isDefault"]
    for field in required_fields:
        if field not in last_address:
            return False

    if last_address.get("isDefault") != True:
        return False

    return True


if __name__ == "__main__":
    result = validate_task_twenty_seven()
    print(result)
