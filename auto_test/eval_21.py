import json
import os
import subprocess


def validate_task_twenty_one(result=None, device_id=None, backup_dir=None):
    """验证任务二十一：查看可复购的商品，找到价格最低的商品并加入购物车。"""
    cart_data_file_path = os.path.join(backup_dir, "cart_data.json") if backup_dir else "cart_data.json"

    cmd = ["adb"]
    if device_id:
        cmd.extend(["-s", device_id])
    cmd.extend(["exec-out", "run-as", "com.example.amazon_sim", "cat", "files/cart_data.json"])
    subprocess.run(cmd, stdout=open(cart_data_file_path, "w"))

    try:
        with open(cart_data_file_path, "r", encoding="utf-8") as f:
            data = json.load(f)
            items = data if isinstance(data, list) else []
    except:
        return False

    if not items:
        return False

    first_item = items[0]
    if isinstance(first_item, dict) and first_item.get("productId") == "prod_012":
        return True

    return False


if __name__ == "__main__":
    result = validate_task_twenty_one()
    print(result)
