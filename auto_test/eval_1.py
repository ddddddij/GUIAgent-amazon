import json
import os
import subprocess


def validate_task_one(result=None, device_id=None, backup_dir=None):
    """验证任务一：在首页搜索'ball'，查看搜索结果，将第一个商品加入购物车。"""
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

    for item in items:
        if isinstance(item, dict):
            if item.get("productId") == "prod_012":
                return True

    return False


if __name__ == "__main__":
    result = validate_task_one()
    print(result)
